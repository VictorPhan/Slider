for n in range(3,9):
  print(n)
  s = ""
  for j in range(1,n):
    s += "H "
    for i in range(1,n):
      s += "+ "
    s += '\n'
  
  s += "+ "
  for i in range(1,n):
    s += "V "
  print(s)