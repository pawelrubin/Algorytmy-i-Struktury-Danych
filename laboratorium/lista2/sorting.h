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
#define MQUICK 4

typedef struct Settings {
  int asc_flag;
  int k;
  int type;
  char* file_name;
} Settings;

typedef struct Data {
  size_t n;
  int* array;
} Data;

typedef struct Stats {
  int size_n;
  int cmp_count;
  int shift_count;
  float time;
} Stats;

void swap(int* a, int* b);

Settings* init_settings();

Settings* get_settings(int argc, char** argv);

Data* init_data(size_t n);

Data* get_data();

void debug(Settings* settings, Data* data);

void print_array(int* array, size_t size);

int* find_min(int* array, size_t size);

Stats* insertion_sort(int* array, size_t size);

Stats* select_sort(int* array, size_t size);

Stats* heap_sort(int* array, size_t size);

Stats* quick_sort(int* array, size_t size);

Stats* mquick_sort(int* array, size_t size);

#endif 