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
void merge_stats(Stats* s1, Stats* s2);
Stats* new_stats();

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

void mquick_sort_asc(int* array, int low, int high, Stats* stats) {
  stats->cmp_count++;
  if (low < high) {
    stats->cmp_count++;
    if (high <= 17) merge_stats(stats, insertion_sort_asc(array, high + 1));
    int partition_index = partition_asc(array, low, high, stats);
    mquick_sort_asc(array, low, partition_index - 1, stats);
    mquick_sort_asc(array, partition_index + 1, high, stats);
  }
}

void mquick_sort_desc(int* array, int low, int high, Stats* stats) {
  stats->cmp_count++;
  if (low < high) {
    stats->cmp_count++;
    if (high <= 17) merge_stats(stats, insertion_sort_desc(array, high + 1));     
    int partition_index = partition_desc(array, low, high, stats);
    mquick_sort_desc(array, low, partition_index - 1, stats);
    mquick_sort_desc(array, partition_index + 1, high, stats);
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
  for (int n = 100; n <= 10000; n += 100) {
    for (int i = 0; i < k; i++) {
      int* array = rand_array(n);
      int* cpy;
      Stats* stats;

      cpy = copy_array(array, n);
      stats = select_sort(cpy, n, asc_flag);
      save_stats_to_file(stats, file_name, 1);

      cpy = copy_array(array, n);
      stats = insertion_sort(cpy, n, asc_flag);
      save_stats_to_file(stats, file_name, 1);

      cpy = copy_array(array, n);
      stats = heap_sort(cpy, n, asc_flag);
      save_stats_to_file(stats, file_name, 1);

      cpy = copy_array(array, n);
      stats = quick_sort(cpy, n, asc_flag);
      save_stats_to_file(stats, file_name, 1);

      cpy = copy_array(array, n);
      stats = mquick_sort(cpy, n, asc_flag);
      save_stats_to_file(stats, file_name, 1);
    }
  }
}
