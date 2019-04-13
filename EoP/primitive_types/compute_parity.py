
class UnsupportedTypeError(Exception):
    """ Raised when an input is not a supported type of the function"""
    pass


def compute_parity_naive(num):
    """
        Compute parity of a 64-bit integer
        :param num: number to be computed parity
        :return 1 or 0
    """
    if not isinstance(num, int):
        raise UnsupportedTypeError("Input value must be an Integer")

    is_parity = 0
    while num:
        is_parity ^= 1
        num = num & (num - 1)
    return is_parity


def compute_parity_with_small_look_up_table(num):
    if not isinstance(num, int):
       raise UnsupportedTypeError("Input value must be an Integer")

    four_bit_p_table = 0x6996
    # because XOR is associative and commutative
    if num >= 2**32:
        num ^= num >> 32
    if num >= 2**16:
        num ^= num >> 16
    if num >= 2**8:
        num ^= num >> 8
    if num >= 2**4:
        num ^= num >> 4
    num &= 0xf # only care the last 4 bit
    print("num %d" % num)
    return (four_bit_p_table >> num) & 0x1

if __name__ == "__main__":
    num = int(raw_input("Number To Check Parity:"))
    res = compute_parity_with_small_look_up_table(num)
    res = "Odd" if res & 0x1 else "Even"
    print ("Parity of %d is %s" % (num, res))
    print ("Naive Parity of %d is %s" % (num, compute_parity_naive(num)))
