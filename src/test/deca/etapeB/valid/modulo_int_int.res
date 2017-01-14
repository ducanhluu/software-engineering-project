`> [1, 0] Program
   +> ListDeclClass [List with 0 elements]
   `> [1, 0] Main
      +> [2, 0] ListDeclVar [List with 1 elements]
      |  []> [2, 4] DeclVar
      |      +> [2, 0] Identifier (int)
      |      |  definition: type (builtin), type=int
      |      +> [2, 4] Identifier (x)
      |      |  definition: variable defined at [2, 4], type=int
      |      `> [2, 7] Initialization
      |         `> [2, 7] Int (3)
      |            type: int
      `> [4, 0] ListInst [List with 1 elements]
         []> [4, 0] Assign
             type: int
             +> [4, 0] Identifier (x)
             |  definition: variable defined at [2, 4], type=int
             `> [4, 4] Modulo
                type: int
                +> [4, 4] Identifier (x)
                |  definition: variable defined at [2, 4], type=int
                `> [4, 6] Int (2)
                   type: int
