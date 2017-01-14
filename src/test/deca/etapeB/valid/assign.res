`> [1, 0] Program
   +> ListDeclClass [List with 0 elements]
   `> [1, 0] Main
      +> [2, 0] ListDeclVar [List with 1 elements]
      |  []> [2, 4] DeclVar
      |      +> [2, 0] Identifier (int)
      |      |  definition: type (builtin), type=int
      |      +> [2, 4] Identifier (x)
      |      |  definition: variable defined at [2, 4], type=int
      |      `> NoInitialization
      `> [3, 0] ListInst [List with 1 elements]
         []> [3, 0] Assign
             type: int
             +> [3, 0] Identifier (x)
             |  definition: variable defined at [2, 4], type=int
             `> [3, 2] Int (2)
                type: int
