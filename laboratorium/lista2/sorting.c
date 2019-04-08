/**
 * @file sorting.c
 * @author Paweł Rubin
 * @date 2019-03-20
 * 
 * @copyright Copyright (c) 2019
 * 
 */
#include "sorting.h"
#include "tools.h"
#include "heap.h"
#include <unistd.h>
#include <stdio.h>
#include <getopt.h>
#include <string.h>
#include <time.h>

Stats* select_sort_asc(int* array, size_t size);
Stats* select_sort_desc(int* array, size_t size);
Stats* insertion_sort_asc(int* array, size_t size);
Stats* insertion_sort_desc(int* array, size_t size);
Stats* heap_sort_asc(int* array, size_t size);
Stats* heap_sort_desc(int* array, size_t size);
void quick_sort_asc(int* array, int low, int high, Stats* stats);
void quick_sort_desc(int* array, int low, int high, Stats* stats);
void mquick_sort_asc(int* array, int loq, int high, Stats* stats);
void mquick_sort_desc(int* array, int loq, int high, Stats* stats);
int partition_asc(int* array, int low, int high, Stats* stats);
int partition_desc(int* array, int low, int high, Stats* stats);
int mpartition_asc(int* array, int low, int high, Stats* stats);
int mpartition_desc(int* array, int low, int high, Stats* stats);
void merge_stats(Stats* s1, Stats* s2);
Stats* new_stats();
void minsert_sort_asc(int* array, int begin, int end, Stats* stats);

Stats* select_sort(int* array, size_t size, int asc_flag) {
  Stats* stats = new_stats(size, "select");
  clock_t begin = clock();
  if (asc_flag) {
    stats = select_sort_asc(array, size);
  } else {
    stats = select_sort_desc(array, size);
  }
  clock_t end = clock();
  stats->time = (double)(end - begin) / CLOCKS_PER_SEC;
  return stats;
}

Stats* insertion_sort(int* array, size_t size, int asc_flag) {
  Stats* stats = new_stats(size, "insertion");
  clock_t begin = clock();
  if (asc_flag) {
    stats = insertion_sort_asc(array, size);
  } else {
    stats = insertion_sort_desc(array, size);
  }
  clock_t end = clock();
  stats->time = (double)(end - begin) / CLOCKS_PER_SEC;
  return stats;
}

Stats* heap_sort(int* array, size_t size, int asc_flag) {
  Stats* stats = new_stats(size, "heap");
  clock_t begin = clock();
  if (asc_flag) {
    stats = heap_sort_asc(array, size);
  } else {
    stats = heap_sort_desc(array, size);
  }
  clock_t end = clock();
  stats->time = (double)(end - begin) / CLOCKS_PER_SEC;
  return stats;
}

Stats* quick_sort(int* array, size_t size, int asc_flag) {
  Stats* stats = new_stats(size, "quick");
  clock_t begin = clock();
  if (asc_flag) {
    quick_sort_asc(array, 0, size - 1, stats);
  } else {
    quick_sort_desc(array, 0, size - 1, stats);
  }
  clock_t end = clock();
  stats->time = (double)(end - begin) / CLOCKS_PER_SEC;
  return stats;
}

Stats* mquick_sort(int* array, size_t size, int asc_flag) {
  Stats* stats = new_stats(size, "mquick");
  clock_t begin = clock();
  if (asc_flag) {
    mquick_sort_asc(array, 0, size - 1, stats);
  } else {
    mquick_sort_desc(array, 0, size - 1, stats);
  }
  clock_t end = clock();
  stats->time = (double)(end - begin) / CLOCKS_PER_SEC;
  return stats;
}

Stats* select_sort_asc(int* array, size_t size) {
  Stats* stats = new_stats(size, "select");
  for (int i = 0; i < size; i++) {
    swap(&array[i], find_min(array + i, size - i));
    // comparisons in find_max + 1 in the for loop
    stats->cmp_count += 2 * ((int)size - 1) + 1;
    stats->shift_count++;
  }
  stats->cmp_count++;
  return stats;
}

Stats* select_sort_desc(int* array, size_t size) {
  Stats* stats = new_stats(size, "select");
  for (int i = 0;  i < size; i++) {
    swap(&array[i], find_max(array + i, size - i));
    // comparisons in find_max + 1 in the for loop
    stats->cmp_count += 2 * ((int)size - 1) + 1; 
    stats->shift_count++;
  }
  stats->cmp_count++;
  return stats;
}

Stats* insertion_sort_asc(int* array, size_t size) {
  Stats* stats = new_stats(size, "insertion");
  for (int i = 0; i < size; i++) {
    int j = i;
    while (j > 0 && array[j-1] > array[j]) {
      swap(&array[j], &array[j-1]);
      j--;
      stats->cmp_count += 2;
      stats->shift_count++;
    }
    stats->cmp_count += 2; // last cmp in while, cmp in for
  }
  stats->cmp_count++; // last cmp in for
  return stats;
}

Stats* insertion_sort_desc(int* array, size_t size) {
  Stats* stats = new_stats(size, "insertion");
  for (int i = 0; i < size; i++) {
    int j = i;
    while (j > 0 && array[j-1] < array[j]) {
      swap(&array[j], &array[j-1]);
      j--;
      stats->cmp_count += 2;
      stats->shift_count++;
    }
    stats->cmp_count += 2; // last cmp in while, cmp in for
  }
  stats->cmp_count++; // last cmp in for
  return stats;
}

Stats* heap_sort_asc(int* array, size_t size) {
  Stats* stats = new_stats(size, "heap");
  build_max_heap(array, size, stats);
  for (int i = (size - 1); i >= 0; i--) {
    swap(&array[0], &array[i]);
    max_heapify(array, i, 0, stats);
    stats->shift_count++;
  }
  return stats;
}

Stats* heap_sort_desc(int* array, size_t size) {
  Stats* stats = new_stats(size, "heap");
  build_min_heap(array, size, stats);
  for (int i = (size - 1); i >= 0; i--) {
    swap(&array[0], &array[i]);
    min_heapify(array, i, 0, stats);
    stats->shift_count++;
  }
  return stats;
}

void quick_sort_asc(int* array, int low, int high, Stats* stats) {
  stats->cmp_count++;
  if (low < high) {
    int partition_index = partition_asc(array, low, high, stats);
    quick_sort_asc(array, low, partition_index - 1, stats);
    quick_sort_asc(array, partition_index + 1, high, stats);
  }
}

void quick_sort_desc(int* array, int low, int high, Stats* stats) {
  stats->cmp_count++;
  if (low < high) {
    int partition_index = partition_desc(array, low, high, stats);
    quick_sort_desc(array, low, partition_index - 1, stats);
    quick_sort_desc(array, partition_index + 1, high, stats);
  }
}

int partition_asc(int* array, int low, int high, Stats* stats) {
  int pivot = array[high];
  int i = (low - 1);

  for (int j = low; j <= (high - 1); j++) {
    stats->cmp_count += 2; // comparisons in the for loop and the if statement
    if (array[j] <= pivot) {
      i++;
      swap(&array[i], &array[j]);
      stats->shift_count++;
    }
  }
  stats->cmp_count++; // last comparison while exiting the loop
  swap(&array[i + 1], &array[high]);
  stats->shift_count++;
  
  return (i + 1);
}

int partition_desc(int* array, int low, int high, Stats* stats) {
  int pivot = array[high];
  int i = (low - 1);

  for (int j = low; j <= (high - 1); j++) {
    stats->cmp_count += 2; // comparisons in the for loop and the if statement
    if (array[j] >= pivot) {
      i++;
      swap(&array[i], &array[j]);
      stats->shift_count++;
    }
  }
  stats->cmp_count++; // last comparison while exiting the loop
  swap(&array[i + 1], &array[high]);
  stats->shift_count++;

  return (i + 1);
}

int mpartition_asc(int* array, int low, int high, Stats* stats) {
  int m[] = {array[low], array[(low + high)/2], array[high]};
  merge_stats(stats, insertion_sort_asc(m, 3));
  stats->cmp_count++;
  if (m[1] == array[low]) {
    stats->shift_count++;
    swap(&array[low], &array[high]);
  } else if (m[1] == array[(low + high)/2]) {
    stats->cmp_count++;
    stats->shift_count++;
    swap(&array[(low+high)/2], &array[high]);
  } else {
    stats->cmp_count++;
  }
  int pivot = array[high];
  int i = (low - 1);

  for (int j = low; j <= (high - 1); j++) {
    stats->cmp_count += 2; // comparisons in the for loop and the if statement
    if (array[j] <= pivot) {
      i++;
      swap(&array[i], &array[j]);
      stats->shift_count++;
    }
  }
  stats->cmp_count++; // last comparison while exiting the loop
  swap(&array[i + 1], &array[high]);
  stats->shift_count++;
  
  return (i + 1);
}

int mpartition_desc(int* array, int low, int high, Stats* stats) {
  int m[] = {array[low], array[(low + high)/2], array[high]};
  merge_stats(stats, insertion_sort_asc(m, 3));
  stats->cmp_count++;
  if (m[1] == array[low]) {
    stats->shift_count++;
    swap(&array[low], &array[high]);
  } else if (m[1] == array[(low + high)/2]) {
    stats->cmp_count++;
    stats->shift_count++;
    swap(&array[(low+high)/2], &array[high]);
  } else {
    stats->cmp_count++;
  }
  int pivot = array[high];
  int i = (low - 1);

  for (int j = low; j <= (high - 1); j++) {
    stats->cmp_count += 2; // comparisons in the for loop and the if statement
    if (array[j] >= pivot) {
      i++;
      swap(&array[i], &array[j]);
      stats->shift_count++;
    }
  }
  stats->cmp_count++; // last comparison while exiting the loop
  swap(&array[i + 1], &array[high]);
  stats->shift_count++;

  return (i + 1);
}

void mquick_sort_asc(int* array, int low, int high, Stats* stats) {
   stats->cmp_count++;
  if (low < high) {
    stats->cmp_count++;
    if ((high - low) < 16) {
      minsert_sort_asc(array, low, high + 1, stats);
    } else {
      int partition_index = mpartition_asc(array, low, high, stats);
      mquick_sort_asc(array, low, partition_index - 1, stats);
      mquick_sort_asc(array, partition_index + 1, high, stats);
    }
  }
}

void mquick_sort_desc(int* array, int low, int high, Stats* stats) {
  stats->cmp_count++;
  if (low < high) {
    stats->cmp_count++;
    if ((high-low) < 16) {
      minsert_sort_desc(array, low, high + 1, stats);
    } else {
      int partition_index = mpartition_desc(array, low, high, stats);
      mquick_sort_desc(array, low, partition_index - 1, stats);
      mquick_sort_desc(array, partition_index + 1, high, stats);
    }
  }
}

void merge_stats(Stats* s1, Stats* s2) {
  s1->cmp_count += s2->cmp_count;
  s1->shift_count += s2->shift_count;
  s1->time += s2->time;
}

Stats* new_stats(size_t size, char* type) {
  Stats *stats = malloc(sizeof(Stats));
  stats->type = type;
  stats->size = size;
  stats->cmp_count = 0;
  stats->shift_count = 0;
  stats->time = 0;
  return stats;
}

void print_stats(Stats* stats) {
  printf("Stats:\n");
  printf("  size: %d", stats->size);
  printf("  cmp_count: %d", stats->cmp_count); 
  printf("  shift_count: %d", stats->shift_count); 
  printf("  time: %f", stats->time); 
}

void run_sorts(int k, char* file_name, int asc_flag) {
  create_file(file_name);
  for (int n = 100; n <= 10000; n += 100) {
    printf("running sorts for n = %d\n", n);
    Stats* select_stats = new_stats(n, "select");
    Stats* insertion_stats = new_stats(n, "insertion");
    Stats* heap_stats = new_stats(n, "heap");
    Stats* quick_stats = new_stats(n, "quick");
    Stats* mquick_stats = new_stats(n, "mquick");

    for (int i = 0; i < k; i++) {
      int* array = rand_array(n);
      size_t size = n * sizeof(int);
      int* copy = malloc(size);

      memcpy(copy, array, size);
      averagify(select_stats, select_sort(copy, n, asc_flag), k);

      memcpy(copy, array, size);
      averagify(insertion_stats, insertion_sort(copy, n, asc_flag), k);

      memcpy(copy, array, size);
      averagify(heap_stats, heap_sort(copy, n, asc_flag), k);

      memcpy(copy, array, size);
      averagify(quick_stats, quick_sort(copy, n, asc_flag), k);

      memcpy(copy, array, size);
      averagify(mquick_stats, mquick_sort(copy, n, asc_flag), k);
      
      free(array);
      free(copy);
    }

    save_stats_to_file(select_stats, file_name, 1);
    save_stats_to_file(insertion_stats, file_name, 1);
    save_stats_to_file(heap_stats, file_name, 1);
    save_stats_to_file(quick_stats, file_name, 1);
    save_stats_to_file(mquick_stats, file_name, 1);
    free(select_stats);
    free(insertion_stats);
    free(heap_stats);
    free(quick_stats);
    free(mquick_stats);
  }
}

void averagify(Stats* s1, Stats* s2, int n) {
  s1->cmp_count += (s2->cmp_count - s1->cmp_count) / (n + 1);
  s1->shift_count += (s2->shift_count - s1->shift_count) / (n + 1);
  s1->time += (s2->time - s1->time) / (n + 1);
  free(s2);
}

void minsert_sort_asc(int* array, int begin, int end, Stats* stats) {
  for (int i = begin; i < end; i++) {
    int j = i;
    while (j > 0 && array[j-1] > array[j]) {
      swap(&array[j], &array[j-1]);
      j--;
      stats->cmp_count += 2;
      stats->shift_count++;
    }
    stats->cmp_count += 2; // last cmp in while, cmp in for
  }
  stats->cmp_count++; 
}

void minsert_sort_desc(int* array, int begin, int end, Stats* stats) {
  for (int i = begin; i < end; i++) {
    int j = i;
    while (j > 0 && array[j-1] < array[j]) {
      swap(&array[j], &array[j-1]);
      j--;
      stats->cmp_count += 2;
      stats->shift_count++;
    }
    stats->cmp_count += 2; // last cmp in while, cmp in for
  }
  stats->cmp_count++; 
}