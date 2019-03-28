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
  int size_n;
  int cmp_count;
  int shift_count;
  float time;
} Stats;

Stats* insertion_sort(int* array, size_t size, int asc_flag);
Stats* insertion_sort_asc(int* array, size_t size);
Stats* insertion_sort_desc(int* array, size_t size);

Stats* select_sort(int* array, size_t size, int asc_flag);
Stats* select_sort_asc(int* array, size_t size);
Stats* select_sort_desc(int* array, size_t size);

Stats* heap_sort(int* array, size_t size, int asc_flag);
Stats* heap_sort_asc(int* array, size_t size);
Stats* heap_sort_desc(int* array, size_t size);

Stats* quick_sort(int* array, size_t size, int asc_flag);
Stats* quick_sort_asc(int* array, int p, int r);
Stats* quick_sort_desc(int* array, int p, int r);

Stats* mquick_sort(int* array, size_t size, int asc_flag);

#endif 