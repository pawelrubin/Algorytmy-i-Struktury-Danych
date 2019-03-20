/**
 * @file linked_list.c
 * @author Paweł Rubin
 * @brief 
 * @version 0.1
 * @date 2019-02-23
 * 
 * @copyright Copyright (c) 2019
 * 
 */
#include "linked_list.h"
#include <stdlib.h>
#include <stdio.h>

Node new_node() {
  Node node = {
    0,
    NULL,
    NULL
  };
  return node;
}

Linked_list new_singly_linked_list() {
  Linked_list list = {NULL};
  list.insert = insert_singly;
  list.delete = delete_singly;
  list.findMTF = findMTF_singly;
  list.findTRANS = findTRANS_singly;
  return list;
}

void insert_singly(Linked_list *list, int value) {
  Node *to_insert = malloc(sizeof(Node));
  to_insert->value = value;
  to_insert->next = list->head;
  list->head = to_insert;
}

int delete_singly(Linked_list *list, int value) {
  Node *current = list->head;
  int counter = 2;

  if (current != NULL) {
    if (current->value == value) {
      list->head = current->next;
      free(current);
      return counter;
    }
  }

  while (current->next != NULL) {
    counter += 2;
    if (current->next->value == value) {
      Node *to_delete = current->next;
      current->next = current->next->next;
      free(to_delete);
      break;
    }
    current = current->next;
  }

  return counter;
}

int is_empty(Linked_list *list) {
  return (list->head == NULL);
  // if (list->head == NULL) {
  //   return TRUE;
  // } else {
  //   return FALSE;
  // }
}

int findMTF_singly(Linked_list *list, int value) {
  Node *current = list->head;
  int counter = 2;

  if (current != NULL) {
    if (current->value == value) {
      return 2;
    }
  } else {
    return 1;
  }

  while (current->next != NULL) {
    counter += 2;
    if (current->next->value == value) {
      Node *old_head = list->head;
      list->head = current->next;
      current->next = current->next->next;
      list->head->next = old_head;
      break;
    }
    current = current->next;
  }

  return counter;
}

int findTRANS_singly(Linked_list *list, int value) {
  Node *current = list->head;
  int counter = 3;

  if (current != NULL) {
    if (current->value == value) {
      return 2;
    }
  } else {
    return 1;
  }

  if (current->next != NULL) {
    if (current->next->value == value) {
      Node *old_head = list->head;
      list->head = current->next;
      current->next = current->next->next;
      list->head->next = old_head;
      return 4;
    }
  } else {
    return 3;
  }

  while (current->next->next != NULL) {
    counter += 2;
    if (current->next->next->value == value) {
      Node *next = current->next->next->next;
      Node *found = current->next->next;
      current->next->next->next = current->next;
      current->next->next = next;
      current->next = found;
      break;
    }
    current = current->next;
  }

  return counter;
}


void print_list(Linked_list list) {
  Node *current = list.head;

  while (current != NULL) {
    printf("%d -> ", current->value);
    current = current->next;
  }

  printf("NULL\n");
}
