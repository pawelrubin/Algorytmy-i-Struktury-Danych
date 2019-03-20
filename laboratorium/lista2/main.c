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

void debug(Settings* settings, Data* data) {
  printf("Settings (If unset, value = -1 or NULL):\n");
  printf("  asc_flag: %d\n", settings->asc_flag);
  printf("  type: %d\n", settings->type);
  printf("  k: %d\n", settings->k);
  printf("  file_name: %s\n", settings->file_name);

  printf("Data (If unset, value = -1 or NULL):\n");
  printf("  n: %d\n", data->n);
  printf("  array: [");
  for (int i = 0; i < data->n; i++) {
    printf("%d", data->array[i]);
    if (i < data->n-1) {
      printf(", ");
    }
  }
  printf("]\n");
}

Settings* get_settings(int argc, char** argv) {
  Settings *settings = init_settings();
  int c, option_index, this_option_optind;
  struct option long_options[] = {
    {"asc", no_argument, &settings->asc_flag, 1},
    {"desc", no_argument, &settings->asc_flag, 0},
    {"type", required_argument, 0, 't'},
    {"stat", required_argument, 0, 's'},
    {0, 0, 0, 0}
  };

  while ((c = getopt_long(argc, argv, "ts", long_options, &option_index)) != -1) {
    this_option_optind = optind ? optind : 1;
    option_index = 0;

    switch (c) {
      case 't':
        if (strcmp(optarg, "select") == 0) {
          settings->type = SELECT;
        } else if (strcmp(optarg, "insertion") == 0) {
          settings->type = INSERTION;
        } else if (strcmp(optarg, "heap") == 0) {
          settings->type = HEAP;
        } else if (strcmp(optarg, "quick") == 0) {
          settings->type = QUICK;
        } else {
          printf("Uknown type.\n");
          exit(EXIT_FAILURE);
        }
        break;
      case 's':
        settings->file_name = strdup(argv[optind - 1]);
        settings->k = atoi(strdup(argv[optind]));
        break;
    }
  }
  return settings;
} 

Data* get_data() {
  size_t n;
  scanf("%d", &n);
  Data* data = init_data(n);
  for (int i = 0; i < n; i++) {
    scanf("%d", &data->array[i]);
  }
  return data;
}

int main(int argc, char** argv) {
  Settings* settings = get_settings(argc, argv);
  Data* data = get_data();
  debug(settings, data);
  switch (settings->type) {
    case SELECT:
      select_sort(data->array, data->n);
      break;
    case INSERTION:
      select_sort(data->array, data->n);
      break;
    case HEAP:
      select_sort(data->array, data->n);
      break;
    case QUICK:
      select_sort(data->array, data->n);
      break;
  }
  return 0;
}