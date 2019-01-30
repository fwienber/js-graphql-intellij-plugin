package com.intellij.lang.jsgraphql.springel;

import com.intellij.lang.jsgraphql.GraphQLLanguage;
import com.intellij.lang.jsgraphql.psi.GraphQLArgument;
import com.intellij.lang.jsgraphql.psi.GraphQLArguments;
import com.intellij.lang.jsgraphql.psi.GraphQLDirective;
import com.intellij.lang.jsgraphql.psi.GraphQLStringValue;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.InjectedLanguagePlaces;
import com.intellij.psi.LanguageInjector;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.spring.el.SpringELLanguage;
import org.jetbrains.annotations.NotNull;

public class GraphQLSchemaSpringELInjector implements LanguageInjector {
  @Override
  public void getLanguagesToInject(@NotNull PsiLanguageInjectionHost psiLanguageInjectionHost, @NotNull InjectedLanguagePlaces injectedLanguagePlaces) {
    if (ApplicationManager.getApplication().isUnitTestMode()) {
      // intellij unit test env doesn't properly support language injection in combination with formatter tests, so skip injection in that case
      return;
    }

    if (!(psiLanguageInjectionHost instanceof GraphQLStringValue)) {
      return;
    }
    if (!(psiLanguageInjectionHost.getLanguage() == GraphQLLanguage.INSTANCE)) {
      return;
    }
    PsiElement argument = psiLanguageInjectionHost.getParent();
    if (!(argument instanceof GraphQLArgument)) {
      return;
    }
    if (!"from".equals(((GraphQLArgument) argument).getName())) {
      return;
    }
    PsiElement arguments = argument.getParent();
    if (!(arguments instanceof GraphQLArguments)) {
      return;
    }
    PsiElement directive = arguments.getParent();
    if (!(directive instanceof GraphQLDirective)) {
      return;
    }
    if (!"fetch".equals(((GraphQLDirective) directive).getName())) {
      return;
    }
    injectedLanguagePlaces.addPlace(SpringELLanguage.INSTANCE, TextRange.create(1, psiLanguageInjectionHost.getTextLength() - 1), "", "");
  }
}
