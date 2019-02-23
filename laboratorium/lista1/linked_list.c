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
#define TRUE 1 
#define FALSE 0

Node new_node() {
  Node node = {NULL};
  return node;
}

Linked_list new_singly_linked_list() {
  Linked_list list = {NULL};
  list.insert = insert_singly;
  list.delete = delete_singly;
  list.is_empty = is_empty;
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

void delete_singly(Linked_list *list, int value) {
  Node *current = list->head;

  if (current->value == value) {
    list->head = current->next;
  }

  while (current->next != NULL) {
    if (current->next->value == value) {
        current->next = current->next->next;
        return;
    }
    current = current->next;
  }
}

int is_empty(Linked_list *list) {
  return (list->head == NULL);
}

int findMTF_singly(Linked_list *list, int value) {
  Node *current = list->head;

  if (current->value == value) {
    return TRUE;
  }

  while (current->next != NULL) {
    if (current->next->value == value) {
      Node *old_head = list->head;
      list->head = current->next;
      current->next = current->next->next;
      list->head->next = old_head;
      return TRUE;
    }
    current = current->next;
  }

  return FALSE;
}
/**
 * @brief 
 * 
 * @param list 
 * @param value 
 * @return int 
 */
int findTRANS_singly(Linked_list *list, int value) {
  Node *current = list->head;

  if (current->value == value) {
    return TRUE;
  }

  if (current->next->value == value) {
    Node *old_head = list->head;
    list->head = current->next;
    current->next = current->next->next;
    list->head->next = old_head;
    return TRUE;
  }

  while (current->next->next != NULL) {
    if (current->next->next->value == value) {
      Node *next = current->next->next->next;
      Node *found = current->next->next;
      current->next->next->next = current->next;
      current->next->next = next;
      current->next = found;
      return TRUE;
    }
    current = current->next;
  }

  return FALSE;
}


void print_list(Linked_list list) {
    Node *current = list.head;

    while (current != NULL) {
        printf("%d, ", current->value);
        current = current->next;
    }

    printf("\n");
}

