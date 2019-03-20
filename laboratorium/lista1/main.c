#include "linked_list.h"
#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#define SIZE 100

void fill_random(Linked_list *list, int size) {
  int numbers[size];

  for (int i = 0; i < size; i++) {
    numbers[i] = FALSE;
  }

  srand(time(NULL));

  int counter = 0;
  while (counter < size) {
    int number = rand()%size;
    if (!numbers[number]) {
      list->insert(list, number + 1);
      numbers[number] = TRUE;
      counter++;
    }
  }
}

int main(void) {
  Linked_list list = new_singly_linked_list();
  fill_random(&list, SIZE);
  int MTFsum = 0;
  
  for (int i = SIZE; i > 0; i--) {
    for (int j = 1; j <= SIZE; j++) {
      MTFsum += list.findMTF(&list, j);
    }
    MTFsum += list.delete(&list, i);
  }

  printf("\n");
  Linked_list list2 = new_singly_linked_list();
  fill_random(&list2, SIZE);
  int TRANSsum = 0;
  for (int i = SIZE; i > 0; i--) {
    for (int j = 1; j <= SIZE; j++) {
      TRANSsum += list2.findTRANS(&list2, j);
    }
    TRANSsum += list.delete(&list2, i);
  }
  printf("MTF  : %f\n", (double)MTFsum / (SIZE*SIZE));
  printf("TRANS: %f\n", (double)TRANSsum / (SIZE*SIZE));

  return 0;
}
