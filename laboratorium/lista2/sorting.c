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

Stats* select_sort(int* array, size_t size, int asc_flag) {
  if (asc_flag) {
    return select_sort_asc(array, size);
  } else {
    return select_sort_desc(array, size);
  }
}

Stats* select_sort_asc(int* array, size_t size) {
  Stats* stats = malloc(sizeof(Stats));
  for (int i = 0; i < size; i++) {
    swap(&array[i], find_min(array + i, size - i));
  }
  return stats;
}

Stats* select_sort_desc(int* array, size_t size) {
  Stats* stats = malloc(sizeof(Stats));
  for (int i = 0; i < size; i++) {
    swap(&array[i], find_max(array + i, size - i));
  }
  return stats;
}

Stats* insertion_sort(int* array, size_t size, int asc_flag) {
  if (asc_flag) {
    return insertion_sort_asc(array, size);
  } else {
    return insertion_sort_desc(array, size);
  }
}

Stats* insertion_sort_asc(int* array, size_t size) {
  Stats* stats = malloc(sizeof(Stats));
  for (int i = 0; i < size; i++) {
    int j = i;
    while (j > 0 && array[j-1] > array[j]) {
      swap(&array[j], &array[j-1]);
      j--;
    }
  }
  return stats;
}

Stats* insertion_sort_desc(int* array, size_t size) {
  Stats* stats = malloc(sizeof(Stats));
  for (int i = 0; i < size; i++) {
    int j = i;
    while (j > 0 && array[j-1] < array[j]) {
      swap(&array[j], &array[j-1]);
      j--;
    }
  }
  return stats;
}


Stats* heap_sort(int* array, size_t size, int asc_flag) {
  if (asc_flag) {
    return heap_sort_asc(array, size);
  } else {
    return heap_sort_desc(array, size);
  }
}

Stats* heap_sort_asc(int* array, size_t size) {
  Stats* stats = malloc(sizeof(Stats));
  build_max_heap(array, size);
  for (int i = size - 1; i >= 0; i--) {
    swap(&array[0], &array[i]);
    max_heapify(array, i, 0);
  }
  return stats;
}

Stats* heap_sort_desc(int* array, size_t size) {
  Stats* stats = malloc(sizeof(Stats));
  build_min_heap(array, size);
  for (int i = size - 1; i >= 0; i--) {
    swap(&array[0], &array[i]);
    min_heapify(array, i, 0);
  }
  return stats;
}

int partition_asc(int* array, int low, int high) {
  int pivot = array[high];
  int i = (low - 1);

  for (int j = low; j <= (high - 1); j++) {
    if (array[j] <= pivot) {
      i++;
      swap(&array[i], &array[j]);
    }
  }
  swap(&array[i + 1], &array[high]);
  return (i + 1);
}

int partition_desc(int* array, int low, int high) {
  int pivot = array[high];
  int i = (low - 1);

  for (int j = low; j <= (high - 1); j++) {
    if (array[j] >= pivot) {
      i++;
      swap(&array[i], &array[j]);
    }
  }
  swap(&array[i + 1], &array[high]);
  return (i + 1);
}

Stats* quick_sort_asc(int* array, int low, int high) {
  Stats* stats = malloc(sizeof(Stats));  
  if (low < high) {
    int partition_index = partition_asc(array, low, high);
    quick_sort_asc(array, low, partition_index - 1);
    quick_sort_asc(array, partition_index + 1, high);
  }
  return stats;
}

Stats* quick_sort(int* array, size_t size, int asc_flag) {
  if (asc_flag) {
    return quick_sort_asc(array, 0, size - 1);
  } else {
    return quick_sort_desc(array, 0, size - 1);
  }
}

Stats* quick_sort_desc(int* array, int low, int high) {
  Stats* stats = malloc(sizeof(Stats));  
  if (low < high) {
    int partition_index = partition_desc(array, low, high);
    quick_sort_desc(array, low, partition_index - 1);
    quick_sort_desc(array, partition_index + 1, high);
  }
  return stats;
}