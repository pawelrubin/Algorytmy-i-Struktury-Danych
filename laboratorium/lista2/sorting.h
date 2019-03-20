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

#define SELECT 0
#define INSERTION 1
#define HEAP 2
#define QUICK 3

typedef struct Stats {
    int size_n;
    int cmp_count;
    int shift_count;
    float time;
} Stats;

Stats insertion_sort(int** array, size_t size);

Stats select_sort(int** array, size_t size);

Stats heap_sort(int** array, size_t size);

Stats quick_sort(int** array, size_t size);

#endif 