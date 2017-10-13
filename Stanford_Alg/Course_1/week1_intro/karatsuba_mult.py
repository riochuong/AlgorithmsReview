import numpy as np
def karatsuba (a, b):
    # assume b and a should have same len
    print("karatusb ",a,b)
    if len(str(a)) == len(str(b)) == 1:
        print("return ",a*b)
        return a * b
    size = len(str(a))
    # now need to figure out size 
    m = int(np.ceil(size/2))
    x1,x0 = divmod(a,10**m)
    y1,y0 = divmod(b,10**m)
    #print ("x1,x0,y1,y0 ",x1, x0, y1, y0)
    a = karatsuba(x1,y1)
    #print ("=======")
    c = karatsuba(x0,y0)
    #print ("=======")
    b = karatsuba((x1 + x0),(y1 + y0)) - a - c
    #print ("=======")
    res = a*(10 ** (2*m)) + b * (10 ** m) + c
    print ("return ",res)
    return res
    
if __name__ == '__main__':
	karatsuba(3141592653589793238462643383279502884197169399375105820974944592,
          2718281828459045235360287471352662497757247093699959574966967627)
