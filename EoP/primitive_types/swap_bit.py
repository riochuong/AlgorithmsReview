class UnsupportedTypeError(Exception):
    pass

def swap_bit(num, i, j):
    if not (isinstance(num, int) and (isinstance(i, int) and isinstance(j,int))):
        raise UnsupportedTypeError()

    # test bit i, j to see if we need to swap
    if (num & (1 << i)) != (num & (1 << j)):
        # use XOR to flip both bits as they are different
        num ^= (1 << i) | ( 1 << j)
    return num
