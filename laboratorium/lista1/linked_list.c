#include <stdlib.h>
#include <stdio.h>
#define TRUE 1
#define FALSE 0

typedef struct Node Node;
typedef struct Linked_list Linked_list;

struct Node {
  int value;
  Node *next;
};

Node new_node() {
  Node node = {NULL};
  return node;
}

struct Linked_list {
  Node *head;
  
  void (*insert)(Linked_list *, int value);
  void (*delete)(Linked_list *, int value);
  int (*is_empty)(Linked_list *);
  int (*findMTF)(Linked_list *, int value);
  int (*findTRANS)(Linked_list *, int value);
};

void insert(Linked_list *list, int value) {
  Node *to_insert = malloc(sizeof(Node));
  to_insert->value = value;
  to_insert->next = list->head;
  list->head = to_insert;
}

void delete(Linked_list *list, int value) {
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

int findMTF(Linked_list *list, int value) {
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

int findTRANS(Linked_list *list, int value) {
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

Linked_list new_singly_linked_list() {
  Linked_list list = {NULL};
  list.insert = insert;
  list.delete = delete;
  list.is_empty = is_empty;
  list.findMTF = findMTF;
  list.findTRANS = findTRANS;

  return list;
}

void print_list(Linked_list list) {
    Node *current = list.head;

    while (current != NULL) {
        printf("%d, ", current->value);
        current = current->next;
    }

    printf("\n");
}

int main(void) {
    Linked_list list = new_singly_linked_list();

    list.insert(&list, 7);
    list.insert(&list, 3);
    list.insert(&list, 1);
    list.insert(&list, 2);
    list.insert(&list, 2137);

    print_list(list);
    list.findTRANS(&list, 2);
    print_list(list);
    list.delete(&list, 2137);
    print_list(list);

    return 0;
}