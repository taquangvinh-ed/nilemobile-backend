# Unit testing `registerUser`

In a unit test, you test only `registerUser`.

The other methods it calls are treated as dependencies, so you **mock** them and decide how they behave in the test.

For `registerUser`, the main dependencies are:

1. `userRepository.findByEmail(...)`
2. `userRepository.findByPhoneNumber(...)`
3. `userMapper.toEntity(request)`
4. `passwordEncoder.encode(...)`
5. `userRepository.findByUsername(...)`
6. `roleRepository.findById(...)`
7. `userRepository.save(...)`

So yes, in the test you assume those methods behave the way you want for that scenario.

Example:

- For a success test, you assume email and phone number do not exist
- You assume the mapper returns a `User`
- You assume the role exists
- You assume `save(...)` returns the saved user

That is why Mockito code looks like this:

```java
when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
when(roleRepository.findById((byte) 2)).thenReturn(Optional.of(customerRole));
```

This means: “for this test, pretend this method returns this value.”

### Important rule

You do **not** test the repository, mapper, or password encoder here.

You only test whether `registerUser`:

- checks duplicates
- generates username
- encodes password
- assigns role
- saves the user
- throws the right exception when needed

### Simple idea

Think of a unit test like this:

- **real code under test**: `registerUser`
- **fake dependencies**: repository, mapper, encoder

That is the normal way to test service methods.
