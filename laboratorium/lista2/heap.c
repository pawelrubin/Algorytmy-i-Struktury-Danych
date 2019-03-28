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
#include <stdlib.h>

extern inline int parent(int i);

extern inline int left(int i);

extern inline int right(int i);

void max_heapify(int* array, size_t size, int i) {
  int l = left(i);
  int r = l + 1;
  int max = i;

  if (l < size && array[l] > array[i]) {
    max = l;    
  } 

  if (r < size && array[r] > array[max]) {
    max = r;
  }

  if (max != i) {
    swap(&array[i], &array[max]);
    max_heapify(array, size, max);
  }
}

void min_heapify(int* array, size_t size, int i) {
  int l = left(i);
  int r = l + 1;
  int min = i;

  if (l < size && array[l] < array[i]) {
    min = l;    
  } 

  if (r < size && array[r] < array[min]) {
    min = r;
  }

  if (min != i) {
    swap(&array[i], &array[min]);
    min_heapify(array, size, min);
  }
}

/**
 * @brief T(n) = O(n)
 * 
 * @param array 
 * @param size 
 */
void build_max_heap(int* array, size_t size) {
  for (int i = size/2 - 1; i >= 0; i--) {
    max_heapify(array, size, i);
  }
}

void build_min_heap(int* array, size_t size) {
  for (int i = size/2 - 1; i >= 0; i--) {
    min_heapify(array, size, i);
  }
}
