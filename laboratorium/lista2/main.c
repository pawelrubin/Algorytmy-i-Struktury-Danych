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

int asc_flag = -1;
int k = -1;
int type = -1;
char* file_name;

void debug() {
  printf("If unset, value = -1\n");
  printf("  asc_flag: %d\n", asc_flag);
  printf("  type: %d\n", type);
  printf("  k: %d\n", k);
  printf("  file_name: %s\n", file_name);
}

void handle_options(int argc, char** argv) {
  int c, option_index, this_option_optind;
  struct option long_options[] = {
    {"asc", no_argument, &asc_flag, 1},
    {"desc", no_argument, &asc_flag, 0},
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
          type = SELECT;
        } else if (strcmp(optarg, "insertion") == 0) {
          type = INSERTION;
        } else if (strcmp(optarg, "heap") == 0) {
          type = HEAP;
        } else if (strcmp(optarg, "quick") == 0) {
          type = QUICK;
        } else {
          printf("Uknown type.\n");
          exit(EXIT_FAILURE);
        }
        break;
      case 's':
        file_name = strdup(argv[optind - 1]);
        k = atoi(strdup(argv[optind]));
        break;
      default:
        break;
    }
  }
  debug();
} 

int main(int argc, char** argv) {
  handle_options(argc, argv);
  
  return 0;
}