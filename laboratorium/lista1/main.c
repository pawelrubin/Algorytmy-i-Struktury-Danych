#include "linked_list.h"

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