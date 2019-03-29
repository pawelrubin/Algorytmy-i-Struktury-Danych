/**
 * @file sorting.h
 * @author Paweł Rubin
 * @date 2019-03-11
 * 
 * @copyright Copyright (c) 2019
 * 
 */

#ifndef SORTING_H
#define SORTING_H

#include <stdlib.h>

typedef struct Stats {
  size_t size;
  int cmp_count;
  int shift_count;
  double time;
} Stats;

Stats* new_stats(size_t size);

void merge_stats(Stats* s1, Stats* s2);

Stats* insertion_sort(int* array, size_t size, int asc_flag);

Stats* select_sort(int* array, size_t size, int asc_flag);

Stats* heap_sort(int* array, size_t size, int asc_flag);

Stats* quick_sort(int* array, size_t size, int asc_flag);

Stats* mquick_sort(int* array, size_t size, int asc_flag);

void print_stats(Stats* stats);

#endif 