`> [1, 0] Program
   +> ListDeclClass [List with 0 elements]
   `> [1, 0] Main
      +> [2, 0] ListDeclVar [List with 1 elements]
      |  []> [2, 4] DeclVar
      |      +> [2, 0] Identifier (int)
      |      |  definition: type (builtin), type=int
      |      +> [2, 4] Identifier (x)
      |      |  definition: variable defined at [2, 4], type=int
      |      `> [2, 6] Initialization
      |         `> [2, 6] Int (1)
      |            type: int
      `> [3, 0] ListInst [List with 1 elements]
         []> [3, 0] IfThenElse
             +> [3, 3] And
             |  type: boolean
             |  +> [3, 3] Equals
             |  |  type: boolean
             |  |  +> [3, 3] Identifier (x)
             |  |  |  definition: variable defined at [2, 4], type=int
             |  |  `> [3, 6] Int (2)
             |  |     type: int
             |  `> [3, 12] Lower
             |     type: boolean
             |     +> [3, 12] Int (2)
             |     |  type: int
             |     `> [3, 14] Int (3)
             |        type: int
             +> [4, 0] ListInst [List with 1 elements]
             |  []> [4, 0] Assign
             |      type: int
             |      +> [4, 0] Identifier (x)
             |      |  definition: variable defined at [2, 4], type=int
             |      `> [4, 2] Int (4)
             |         type: int
             `> ListInst [List with 0 elements]
