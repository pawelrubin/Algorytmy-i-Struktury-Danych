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

int main(int argc, char** argv) {
  Settings* settings = get_settings(argc, argv);
  Data* data = get_data();
  debug(settings, data);
  switch (settings->type) {
    case SELECT:
      select_sort(data->array, data->n);
      break;
    case INSERTION:
      // insertion_sort(data->array, data->n);
      break;
    case HEAP:
      // heap_sort(data->array, data->n);
      break;
    case QUICK:
      // quick_sort(data->array, data->n);
      break;
  }
  return 0;
}