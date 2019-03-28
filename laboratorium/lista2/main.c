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
  debug(settings, data);
  switch (settings->type) {
    case SELECT:
      select_sort(data->array, data->n, settings->asc_flag);
      break;
    case INSERTION:
      insertion_sort(data->array, data->n, settings->asc_flag);
      break;
    case HEAP:
      heap_sort(data->array, data->n, settings->asc_flag);
      break;
    case QUICK:
      quick_sort(data->array, data->n, settings->asc_flag);
      break;
    case MQUICK:
      mquick_sort(data->array, data->n, settings->asc_flag);
      break;
  }

  debug(settings, data);

  return 0;
}
