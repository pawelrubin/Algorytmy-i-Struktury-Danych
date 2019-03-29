/**
 * @file tools.c
 * @author Paweł Rubin
 * @date 2019-03-28
 * 
 * @copyright Copyright (c) 2019
 * 
 */

#include "tools.h"

Settings* init_settings() {
  Settings* settings = malloc(sizeof(Settings));
  settings->asc_flag = TRUE;
  settings->type = -1;
  settings->k = -1;
  settings->file_name = NULL;
  return settings;
}

Data* init_data(size_t n) {
  Data* data = malloc(sizeof(Data));
  data->n = n;
  data->array = malloc(n * sizeof(int));
  return data;
}

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

void print_array(int* array, size_t size) {
  printf("[");
  for (int i = 0; i < size; i++) {
    printf("%d", array[i]);
    if (i < size-1) {
      printf(", ");
    }
  }
  printf("]\n");
}

Settings* get_settings(int argc, char** argv) {
  Settings *settings = init_settings();
  int c, option_index;
  struct option long_options[] = {
    {"asc", no_argument, &settings->asc_flag, 1},
    {"desc", no_argument, &settings->asc_flag, 0},
    {"type", required_argument, 0, 't'},
    {"stat", required_argument, 0, 's'},
    {0, 0, 0, 0}
  };

  while ((c = getopt_long(argc, argv, "ts", long_options, &option_index)) != -1) {
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
        } else if (strcmp(optarg, "mquick") == 0) {
          settings->type = MQUICK;
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
  scanf("%d", (int*)&n);
  Data* data = init_data(n);
  for (int i = 0; i < n; i++) {
    scanf("%d", &data->array[i]);
  }
  return data;
}

void swap(int* a, int* b) {
  int temp = *a;
  *a = *b;
  *b = temp;
}


int* find_min(int* array, size_t size) {
  int* min = array;
  for (int i = 1; i < size; i++) {
    if (array[i] < *min) {
      min = &array[i];
    }
  }
  return min;
}

int* find_max(int* array, size_t size) {
  int* max = array;
  for (int i = 1; i < size; i++) {
    if (array[i] > *max) {
      max = &array[i];
    }
  }
  return max;
}
