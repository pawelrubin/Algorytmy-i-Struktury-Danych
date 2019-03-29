/**
 * @file heap.h
 * @author Paweł Rubin
 * @date 2019-03-28
 * 
 * @copyright Copyright (c) 2019
 * 
 */

#ifndef HEAP_H
#define HEAP_H

#include "sorting.h"
#include <stdlib.h>

inline int parent(int i) {
  return i / 2;
}

inline int left(int i) {
  return 2 * i + 1;
}

inline int right(int i) {
  return 2 * i + 2;
}

/**
 * @brief T(n) = O(logn)
 * 
 * @param array 
 * @param size 
 * @param i 
 */
void max_heapify(int* array, size_t size, int i, Stats* stats);
void min_heapify(int* array, size_t size, int i, Stats* stats);

void build_max_heap(int* array, size_t size, Stats* stats);
void build_min_heap(int* array, size_t size, Stats* stats);


#endif