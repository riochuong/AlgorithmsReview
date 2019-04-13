
def find_closest_int_with_same_weight(num):
    ''' we gonna find the closest zero and one pair from the right and flip the bit'''
    last_bit = None
    current_bit = None
    for i in range(63):
        if (num & (1 << i)) ^ (num & (1 << (i + 1))):
            return (num ^ (1 << i)) ^ (1 << (i + 1))

    print("WARN: Number is either all-zero or all-one bits")
    return num

if __name__ == "__main__":
    num = int(raw_input("Number:"), 2)
    print("Orig %s" % str(bin(num)))
    print("Closest %s" % str(bin(find_closest_int_with_same_weight(num))))

