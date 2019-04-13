

def three_ways_partition(arr, i):
    """
        Partition array around arr[i] elements
    :param: arr -> list of arr elements
    :param: i -> index of element we want to partition around
    """
    pivot = arr[i]
    l = 0
    r = len(arr) - 1
    k = l

    def swap(arr, i, j):
        tmp = arr[i]
        arr[i] = arr[j]
        arr[j] = tmp

    while k < r:
        while arr[l] < pivot:
            l += 1
            k = l
        while arr[r] > pivot:
            r -= 1
        if arr[l] > arr[r]:
            swap(arr, l, r)
        else:
            # this case a[l] == a[r] == pivot
            while (k < r) and (arr[k] == pivot):
                k += 1
            # check what can happen here
            if k == r :
                break
            if arr[k] < pivot:
                swap(arr, k, l)
            else:
                swap(arr, k, r)

    print (arr)

if __name__ == "__main__":
    three_ways_partition([5,11,22,5,6,0,5,2], 3)
