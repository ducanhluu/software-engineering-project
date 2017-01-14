`> [1, 0] Program
   +> ListDeclClass [List with 0 elements]
   `> [1, 0] Main
      +> [2, 0] ListDeclVar [List with 2 elements]
      |  []> [2, 6] DeclVar
      |  ||  +> [2, 0] Identifier (float)
      |  ||  |  definition: type (builtin), type=float
      |  ||  +> [2, 6] Identifier (x)
      |  ||  |  definition: variable defined at [2, 6], type=float
      |  ||  `> NoInitialization
      |  []> [3, 6] DeclVar
      |      +> [3, 0] Identifier (float)
      |      |  definition: type (builtin), type=float
      |      +> [3, 6] Identifier (y)
      |      |  definition: variable defined at [3, 6], type=float
      |      `> NoInitialization
      `> [4, 0] ListInst [List with 1 elements]
         []> [4, 0] Assign
             type: float
             +> [4, 0] Identifier (x)
             |  definition: variable defined at [2, 6], type=float
             `> [4, 2] Plus
                type: float
                +> [4, 2] Identifier (x)
                |  definition: variable defined at [2, 6], type=float
                `> [4, 4] Identifier (y)
                   definition: variable defined at [3, 6], type=float
