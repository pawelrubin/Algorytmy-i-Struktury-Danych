#include <time.h>
#include <stdlib.h>
#include <stdio.h>

#include "hyperCube.h"

int main(int argc, char **argv) {
	int SIZE = atoi(argv[1]);
	srand(time(NULL));
	
  // test_gerating(SIZE, 100);
  int** cube = generate_cube(SIZE);
  print_cube(cube, SIZE);

	return 0;
}