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
      |  ||     `> [2, 6] Int (3)
      |  ||        type: int
      |  []> [3, 4] DeclVar
      |      +> [3, 0] Identifier (int)
      |      |  definition: type (builtin), type=int
      |      +> [3, 4] Identifier (y)
      |      |  definition: variable defined at [3, 4], type=int
      |      `> [3, 6] Initialization
      |         `> [3, 6] Int (2)
      |            type: int
      `> [4, 0] ListInst [List with 4 elements]
         []> [4, 0] Println
         ||  `> [4, 8] ListExpr [List with 1 elements]
         ||     []> [4, 8] Identifier (x)
         ||         definition: variable defined at [2, 4], type=int
         []> [6, 0] Println
         ||  `> [6, 8] ListExpr [List with 1 elements]
         ||     []> [6, 8] Identifier (y)
         ||         definition: variable defined at [3, 4], type=int
         []> [7, 0] Assign
         ||  type: int
         ||  +> [7, 0] Identifier (x)
         ||  |  definition: variable defined at [2, 4], type=int
         ||  `> [7, 2] Plus
         ||     type: int
         ||     +> [7, 2] Int (5)
         ||     |  type: int
         ||     `> [7, 4] Identifier (x)
         ||        definition: variable defined at [2, 4], type=int
         []> [8, 0] Println
             `> [8, 8] ListExpr [List with 1 elements]
                []> [8, 8] Identifier (x)
                    definition: variable defined at [2, 4], type=int
