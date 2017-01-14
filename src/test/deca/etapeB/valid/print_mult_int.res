`> [1, 0] Program
   +> ListDeclClass [List with 0 elements]
   `> [1, 0] Main
      +> [2, 0] ListDeclVar [List with 2 elements]
      |  []> [2, 4] DeclVar
      |  ||  +> [2, 0] Identifier (int)
      |  ||  |  definition: type (builtin), type=int
      |  ||  +> [2, 4] Identifier (x)
      |  ||  |  definition: variable defined at [2, 4], type=int
      |  ||  `> [2, 8] Initialization
      |  ||     `> [2, 8] Int (2)
      |  ||        type: int
      |  []> [3, 4] DeclVar
      |      +> [3, 0] Identifier (int)
      |      |  definition: type (builtin), type=int
      |      +> [3, 4] Identifier (y)
      |      |  definition: variable defined at [3, 4], type=int
      |      `> [3, 8] Initialization
      |         `> [3, 8] Int (3)
      |            type: int
      `> [4, 0] ListInst [List with 1 elements]
         []> [4, 0] Print
             `> [4, 6] ListExpr [List with 1 elements]
                []> [4, 6] Multiply
                    type: int
                    +> [4, 6] Identifier (x)
                    |  definition: variable defined at [2, 4], type=int
                    `> [4, 8] Identifier (y)
                       definition: variable defined at [3, 4], type=int
