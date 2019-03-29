/**
 * @file heap.c
 * @author Paweł Rubin
 * @date 2019-03-28
 * 
 * @copyright Copyright (c) 2019
 * 
 */

#include "heap.h"
#include "tools.h"
#include "sorting.h"
#include <stdlib.h>

extern inline int parent(int i);

extern inline int left(int i);

extern inline int right(int i);

void max_heapify(int* array, size_t size, int i, Stats* stats) {
  int l = left(i);
  int r = l + 1;
  int max = i;

  stats->cmp_count++;
  if (l < size && array[l] > array[i]) {
    stats->cmp_count++;
    max = l;    
  } 

  stats->cmp_count++;
  if (r < size && array[r] > array[max]) {
    stats->cmp_count++;
    max = r;
  }

  stats->cmp_count++;
  if (max != i) {
    swap(&array[i], &array[max]);
    stats->shift_count++;
    max_heapify(array, size, max, stats);
  }
}

void min_heapify(int* array, size_t size, int i, Stats* stats) {
  int l = left(i);
  int r = l + 1;
  int min = i;

  stats->cmp_count++;  
  if (l < size && array[l] < array[i]) {
    stats->cmp_count++;  
    min = l;    
  } 

  stats->cmp_count++;  
  if (r < size && array[r] < array[min]) {
    stats->cmp_count++;  
    min = r;
  }

  stats->cmp_count++;  
  if (min != i) {
    swap(&array[i], &array[min]);
    stats->shift_count++;  
    min_heapify(array, size, min, stats);
  }
}

/**
 * @brief T(n) = O(n)
 * 
 * @param array 
 * @param size 
 */
void build_max_heap(int* array, size_t size, Stats* stats) {
  stats->cmp_count++;
  for (int i = size/2 - 1; i >= 0; i--) {
    stats->cmp_count++;
    max_heapify(array, size, i, stats);
  }
}

void build_min_heap(int* array, size_t size, Stats* stats) {
  stats->cmp_count++;
  for (int i = size/2 - 1; i >= 0; i--) {
    stats->cmp_count++;
    min_heapify(array, size, i, stats);
  }
}
