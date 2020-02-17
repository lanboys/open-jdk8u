/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * @test
 * @bug 8029725
 * @summary Lambda reference to containing local class causes javac infinite recursion
 * @author  Robert Field
 * @run main LambdaOuterLocalTest
 */

public class LambdaOuterLocalTest {
    interface F {void f();}

    static F f;
    static StringBuffer sb = new StringBuffer();

    static void assertEquals(Object val, Object expected) {
        if (!val.equals(expected)) {
            throw new AssertionError("expected '" + expected + "' got '" + val + "'");
        }
    }

    public static void main(String[] args) {
        class Local1 {
            public Local1() {
                class Local2 {
                    public Local2() {
                        f = () -> new Local1();
                        sb.append("2");
                    }
                }
                sb.append("1");
                new Local2();
            }
        }
        new Local1();
        f.f();
        assertEquals(sb.toString(), "1212");
    }
}
