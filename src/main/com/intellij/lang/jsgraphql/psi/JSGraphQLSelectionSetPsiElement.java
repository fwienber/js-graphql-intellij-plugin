/**
 * Copyright (c) 2015-present, Jim Kynde Meyer
 * All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.intellij.lang.jsgraphql.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class JSGraphQLSelectionSetPsiElement extends JSGraphQLPsiElement {
    public JSGraphQLSelectionSetPsiElement(@NotNull ASTNode node) {
        super(node);
    }

    public boolean isAnonymousQuery() {
        return getParent() instanceof PsiFile;
    }
}
