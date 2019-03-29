/**
 * @file main.c
 * @author Paweł Rubin
 * @date 2019-03-11
 * 
 * @copyright Copyright (c) 2019
 * 
 */
#include <unistd.h>
#include <stdio.h>
#include <getopt.h>
#include <string.h>
#include "sorting.h"
#include "heap.h"
#include "tools.h"

int main(int argc, char** argv) {
  Settings* settings = get_settings(argc, argv);
  Data* data = get_data();
  Stats* stats;
  debug(settings, data);
  switch (settings->type) {
    case SELECT:
      stats = select_sort(data->array, data->n, settings->asc_flag);
      break;
    case INSERTION:
      stats = insertion_sort(data->array, data->n, settings->asc_flag);
      break;
    case HEAP:
      stats = heap_sort(data->array, data->n, settings->asc_flag);
      break;
    case QUICK:
      stats = quick_sort(data->array, data->n, settings->asc_flag);
      break;
    case MQUICK:
      stats = mquick_sort(data->array, data->n, settings->asc_flag);
      break;
  }
  debug(settings, data);
  print_stats(stats);
  return 0;
}
