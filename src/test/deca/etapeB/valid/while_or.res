`> [1, 0] Program
   +> ListDeclClass [List with 0 elements]
   `> [1, 0] Main
      +> [2, 0] ListDeclVar [List with 2 elements]
      |  []> [2, 4] DeclVar
      |  ||  +> [2, 0] Identifier (int)
      |  ||  |  definition: type (builtin), type=int
      |  ||  +> [2, 4] Identifier (x)
      |  ||  |  definition: variable defined at [2, 4], type=int
      |  ||  `> [2, 6] Initialization
      |  ||     `> [2, 6] Int (0)
      |  ||        type: int
      |  []> [3, 4] DeclVar
      |      +> [3, 0] Identifier (int)
      |      |  definition: type (builtin), type=int
      |      +> [3, 4] Identifier (y)
      |      |  definition: variable defined at [3, 4], type=int
      |      `> [3, 6] Initialization
      |         `> [3, 6] Int (1)
      |            type: int
      `> [4, 0] ListInst [List with 1 elements]
         []> [5, 3] While
             +> [4, 8] Or
             |  type: boolean
             |  +> [4, 8] Equals
             |  |  type: boolean
             |  |  +> [4, 8] Identifier (x)
             |  |  |  definition: variable defined at [2, 4], type=int
             |  |  `> [4, 11] Int (1)
             |  |     type: int
             |  `> [4, 16] Equals
             |     type: boolean
             |     +> [4, 16] Identifier (y)
             |     |  definition: variable defined at [3, 4], type=int
             |     `> [4, 19] Int (1)
             |        type: int
             `> [5, 3] ListInst [List with 1 elements]
                []> [5, 3] Assign
                    type: int
                    +> [5, 3] Identifier (y)
                    |  definition: variable defined at [3, 4], type=int
                    `> [5, 5] Int (0)
                       type: int
