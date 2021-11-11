/****************************************************************************
**
** Copyright (C) 1992-2009 Nokia. All rights reserved.
** Copyright (C) 2009-2021 Dr. Peter Droste, Omix Visualization GmbH & Co. KG. All rights reserved.
**
** This file is part of Qt Jambi.
**
** ** $BEGIN_LICENSE$
**
** GNU Lesser General Public License Usage
** This file may be used under the terms of the GNU Lesser
** General Public License version 2.1 as published by the Free Software
** Foundation and appearing in the file LICENSE.LGPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU Lesser General Public License version 2.1 requirements
** will be met: http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html.
**
** In addition, as a special exception, Nokia gives you certain
** additional rights. These rights are described in the Nokia Qt LGPL
** Exception version 1.0, included in the file LGPL_EXCEPTION.txt in this
** package.
**
** GNU General Public License Usage
** Alternatively, this file may be used under the terms of the GNU
** General Public License version 3.0 as published by the Free Software
** Foundation and appearing in the file LICENSE.GPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU General Public License version 3.0 requirements will be
** met: http://www.gnu.org/copyleft/gpl.html.
**
** $END_LICENSE$
**
** This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
** WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
**
****************************************************************************/

#ifndef CPP_HEADER_GENERATOR
#define CPP_HEADER_GENERATOR

#include "cppgenerator.h"
#include "metajava.h"

class CppHeaderGenerator : public CppGenerator {
    public:
        CppHeaderGenerator(PriGenerator *pri) {
            priGenerator = pri;
        }

        QString fileNameForClass(const AbstractMetaClass *cls) const override;
        QString fileNameForFunctional(const AbstractMetaFunctional *cls) const override;

        void write(QTextStream &s, const AbstractMetaClass *java_class, int nesting_level = 0) override;
        void write(QTextStream &s, const AbstractMetaFunctional *java_functional, int nesting_level = 0) override;
        void writeFunction(QTextStream &s, const AbstractMetaFunction *java_function, Option options = NoOption);
        void writeFunctionOverride(QTextStream &s, const AbstractMetaFunction *java_function, const QString& prefix);
        void writeForwardDeclareSection(QTextStream &s, const AbstractMetaClass *java_class);
        void writeForwardDeclareSection(QTextStream &s, const AbstractMetaFunctional *java_class);
        void writeVariablesSection(QTextStream &s, const AbstractMetaClass *java_class, bool isInterface);
        void writeFieldAccessors(QTextStream &s, const AbstractMetaField *java_field);
        static void writeInjectedCode(QTextStream &s, const AbstractMetaClass *java_class, const QList<CodeSnip::Position>& positions);
        static void writeInjectedCode(QTextStream &s, const AbstractMetaFunctional *java_class, const QList<CodeSnip::Position>& positions);

        bool shouldGenerate(const AbstractMetaClass *java_class) const override {
            return (java_class->generateShellClass()
                    && CppGenerator::shouldGenerate(java_class))
                   || (  !java_class->isFake()
                       && java_class->queryFunctions(AbstractMetaClass::Signals).size() > 0
                       && (java_class->typeEntry()->codeGeneration() & TypeEntry::GenerateCpp));
        }

        bool shouldGenerate(const AbstractMetaFunctional *functional) const override {
            return (!(functional->typeEntry()->codeGeneration() & TypeEntry::GenerateNoShell)
                        && CppGenerator::shouldGenerate(functional));
        }
};

#endif // CPP_HEADER_GENERATOR
