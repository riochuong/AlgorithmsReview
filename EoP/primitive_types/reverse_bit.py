''' Write a function that take 64bit word and return a 64bit word
consisting of the bits of x in reverse order'''


def precompute_reverse_16_bit():
    rev_table = {}
    mask_4 = 0xF
    mask_16 = 0xFFFF
    rev_table_4_bit = {
        0: 0,
        1: 8, # 0b1
        2: 4, # 0b10
        3: 12,# 0b011
        4: 2,#  0b100
        5: 10,# 0b101
        6: 6 ,  # 0b0110
        7: 14,    # 0b0111
        8: 1,
        9: 9, #0b1001
        10:5, # 0b1010
        11:13,   # 0b1011
        12:3,   # 0b1100
        13:11,    # 0b1101
        14:7   ,  # 0b1110
        15:15
    }

    for i in range(2**16 - 1):
       rev_table [i] = rev_table_4_bit[(i >> 12) & 0xF] \
                        | (rev_table_4_bit[(i >> 8) & 0xF] << 4)  \
                        | (rev_table_4_bit[(i >> 4) & 0xF]) << 8 \
                        | (rev_table_4_bit[i & 0xF]) << 12
    return rev_table

def reverse_64_bit(i):
    rev_table_16 = precompute_reverse_16_bit()
    return rev_table_16[(i >> 48) & 0xFFFF] \
                        | (rev_table_16[(i >> 32) & 0xFFFF] << 16)  \
                        | (rev_table_16[(i >> 16) & 0xFFFF]) << 32 \
                        | (rev_table_16[i & 0xFFFF]) << 48

if __name__ == "__main__":
    num = int(raw_input("enter a number:"))
    reverse = reverse_64_bit(num)
    print("orig: %s" % str(bin(num)))
    print("rev: %s" % str(bin(reverse)))
