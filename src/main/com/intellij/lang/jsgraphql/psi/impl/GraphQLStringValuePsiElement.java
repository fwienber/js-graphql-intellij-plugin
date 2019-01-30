package com.intellij.lang.jsgraphql.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.ProperTextRange;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.LiteralTextEscaper;
import com.intellij.psi.PsiLanguageInjectionHost;
import org.jetbrains.annotations.NotNull;

public class GraphQLStringValuePsiElement extends GraphQLValueImpl implements PsiLanguageInjectionHost {

  public GraphQLStringValuePsiElement(ASTNode node) {
    super(node);
  }

  @Override
  public boolean isValidHost() {
    return true;
  }

  @Override
  public PsiLanguageInjectionHost updateText(@NotNull String text) {
    System.out.println("gotcha!");
    return this;
  }

  @NotNull
  @Override
  public LiteralTextEscaper<? extends PsiLanguageInjectionHost> createLiteralTextEscaper() {
    return new LiteralTextEscaper<PsiLanguageInjectionHost>(this) {
      @Override
      public boolean decode(@NotNull TextRange rangeInsideHost, @NotNull StringBuilder outChars) {
        ProperTextRange.assertProperRange(rangeInsideHost);
        outChars.append(this.myHost.getText(), rangeInsideHost.getStartOffset(), rangeInsideHost.getEndOffset());
        return true;
      }

      @Override
      public int getOffsetInHost(int offsetInDecoded, @NotNull TextRange rangeInsideHost) {
        ProperTextRange.assertProperRange(rangeInsideHost);
        int offset = offsetInDecoded + rangeInsideHost.getStartOffset();
        if (offset < rangeInsideHost.getStartOffset()) {
          offset = rangeInsideHost.getStartOffset();
        }

        if (offset > rangeInsideHost.getEndOffset()) {
          offset = rangeInsideHost.getEndOffset();
        }

        return offset;
      }

      @Override
      public boolean isOneLine() {
        return false;
      }
    };
  }
}
