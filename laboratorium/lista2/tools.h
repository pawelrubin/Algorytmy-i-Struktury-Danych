/**
 * @file tools.h
 * @author Paweł Rubin
 * @date 2019-03-28
 * 
 * @copyright Copyright (c) 2019
 * 
 */

#ifndef TOOLS_H
#define TOOLS_H

#include "heap.h"
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <getopt.h>
#include <string.h>

#define TRUE 1
#define FALSE 0

#define SELECT 0
#define INSERTION 1
#define HEAP 2
#define QUICK 3
#define MQUICK 4

typedef struct Settings {
  int asc_flag;
  int k;
  int type;
  int stat_flag;
  char* file_name;
} Settings;

typedef struct Data {
  size_t n;
  int* array;
} Data;

Settings* init_settings();

Settings* get_settings(int argc, char** argv);

Data* init_data(size_t n);

Data* get_data();

void debug(Settings* settings, Data* data);

void print_array(int* array, size_t size);

int* find_min(int* array, size_t size);

int* find_max(int* array, size_t size);

void swap(int* a, int* b);

int* rand_array(size_t n);

int* copy_array(int* array, size_t size);

void save_stats_to_file(Stats* stats, char* filename, int append);

void create_file(char* file_name);

#endif