`> [1, 0] Program
   +> ListDeclClass [List with 0 elements]
   `> [1, 0] Main
      +> [3, 0] ListDeclVar [List with 1 elements]
      |  []> [3, 8] DeclVar
      |      +> [3, 0] Identifier (boolean)
      |      |  definition: type (builtin), type=boolean
      |      +> [3, 8] Identifier (x)
      |      |  definition: variable defined at [3, 8], type=boolean
      |      `> [3, 12] Initialization
      |         `> [3, 12] BooleanLiteral (true)
      |            type: boolean
      `> [4, 0] ListInst [List with 1 elements]
         []> [4, 0] Assign
             type: boolean
             +> [4, 0] Identifier (x)
             |  definition: variable defined at [3, 8], type=boolean
             `> [4, 4] Not
                type: boolean
                `> [4, 5] Identifier (x)
                   definition: variable defined at [3, 8], type=boolean
