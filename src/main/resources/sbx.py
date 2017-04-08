def _prepare_encryption_table():
    """Prepare encryption table for MPQ hash function."""
    seed = 0x00100001
    crypt_table = {}

    for i in range(256):
        index = i
        for j in range(5):
            seed = (seed * 125 + 3) % 0x2AAAAB
            temp1 = (seed & 0xFFFF) << 0x10
            seed = (seed * 125 + 3) % 0x2AAAAB
            temp2 = (seed & 0xFFFF)
            crypt_table[index] = (temp1 | temp2)

            index += 0x100

    return crypt_table

encryption_table = _prepare_encryption_table()
#for key, value in encryption_table.items():
#    print key, value

def _hash(string, hash_type):
    """Hash a string using MPQ's hash function."""
    hash_types = {
        'TABLE_OFFSET': 0,
        'HASH_A': 1,
        'HASH_B': 2,
        'TABLE': 3
    }
    seed1 = 0x7FED7FED
    seed2 = 0xEEEEEEEE

    for ch in string:
        ch = ord(ch.upper())

        value = encryption_table[(hash_types[hash_type] << 8) + ch]
        seed1 = (value ^ (seed1 + seed2)) & 0xFFFFFFFF
        seed2 = ch + seed1 + seed2 + (seed2 << 5) + 3 & 0xFFFFFFFF

        print ch, seed1, seed2

    return seed1
hash = _hash("bar", "TABLE")
print "hash=", hash
#print _hash("bar", "TABLE_OFFSET")
#print _hash("baz", "TABLE_OFFSET")
#print _hash("foo", "HASH_A")
#print _hash("bar", "HASH_A")
#print _hash("baz", "HASH_A")
#print _hash("foo", "HASH_B")
#print _hash("bar", "HASH_B")
#print _hash("baz", "HASH_B")
#print _hash("foo", "TABLE")
#print _hash("bar", "TABLE")
#print _hash("baz", "TABLE")