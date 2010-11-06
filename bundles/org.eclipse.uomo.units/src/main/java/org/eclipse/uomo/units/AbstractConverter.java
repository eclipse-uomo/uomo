/**
 * Copyright (c) 2005, 2010, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Ikayzo and others - initial API and implementation
 */
package org.eclipse.uomo.units;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.List;

import org.unitsofmeasurement.unit.UnitConverter;

/**
 * <p> This class represents a converter of numeric values.</p>
 *
 * <p> It is not required for sub-classes to be immutable
 *     (e.g. currency converter).</p>
 *
 * <p> Sub-classes must ensure unicity of the {@linkplain #IDENTITY identity}
 *     converter. In other words, if the result of an operation is equivalent
 *     to the identity converter, then the unique {@link #IDENTITY} instance
 *     should be returned.</p>
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:jcp@catmedia.us">Werner Keil</a>
 * @version 1.4 ($Revision: 231 $), $Date: 2010-10-13 16:53:37 +0200 (Mi, 13 Okt 2010) $
 */
public abstract class AbstractConverter implements UnitConverter, Serializable {

    /**
     * For cross-version compatibility.
     */
    private static final long serialVersionUID = 2557410026012911803L;

    /**
     * Holds the identity converter (unique). This converter does nothing
     * (<code>ONE.convert(x) == x</code>). This instance is unique.
     */
    public static final UnitConverter IDENTITY = new Identity();

    /**
     * Default constructor.
     */
    protected AbstractConverter() {
    }

    /**
     * Returns the inverse of this converter. If <code>x</code> is a valid
     * value, then <code>x == inverse().convert(convert(x))</code> to within
     * the accuracy of computer arithmetic.
     *
     * @return the inverse of this converter.
     */
    public abstract UnitConverter inverse();

    /**
     * Indicates whether this converter is considered to be the the same as the
     * one specified.
     *
     * @param  cvtr the converter with which to compare.
     * @return <code>true</code> if the specified object is a converter
     *         considered equals to this converter;<code>false</code> otherwise.
     */
    @Override
    public abstract boolean equals(Object cvtr);

    /**
     * Returns a hash code value for this converter. Equals object have equal
     * hash codes.
     *
     * @return this converter hash code value.
     * @see    #equals
     */
    @Override
    public abstract int hashCode();

    /**
     * Concatenates this converter with another converter. The resulting
     * converter is equivalent to first converting by the specified converter
     * (right converter), and then converting by this converter (left converter).
     *
     * <p>Note: Implementations must ensure that the {@link #IDENTITY} instance
     *          is returned if the resulting converter is an identity
     *          converter.</p>
     *
     * @param  converter the other converter.
     * @return the concatenation of this converter with the other converter.
     */
    public UnitConverter concatenate(UnitConverter converter) {
        return (converter == IDENTITY) ? this : new CompoundImpl(this, converter);
    }
	
    @Override
	public boolean isIdentity() {
		return false;
	}

	@Override
	public List<UnitConverter> getCompoundConverters() {
		return Arrays.asList((UnitConverter)new CompoundImpl(this, this));
	}
	
    /**
     * This interface is implemented by converters made up of two
     * separate converters (in matrix notation
     * <code>[compound] = [left] x [right]</code>).
     */
    public interface Compound {

        /**
         * Returns the left converter of this compound converter
         * (the last one performing the conversion).
         *
         * @return the left converter.
         */
        AbstractConverter getLeft();

        /**
         * Returns the right converter of this compound converter
         * (the first one performing the conversion).
         *
         * @return the right converter.
         */
        AbstractConverter getRight();
    }

    /**
     * This inner class represents the identity converter (singleton).
     */
    private static final class Identity extends AbstractConverter {

        /**
         * For cross-version compatibility.
         */
        private static final long serialVersionUID = 7675901502919547460L;

        @Override
        public Identity inverse() {
            return this;
        }

        @Override
        public double convert(double value) {
            return value;
        }

        @Override
        public BigDecimal convert(BigDecimal value, MathContext ctx) {
            return value;
        }

		public Number convert(Number value) {
			return value;
		}
		
        @Override
        public boolean equals(Object cvtr) {
            return this == cvtr; // Unique instance.
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean isLinear() {
            return true;
        }

		@Override
		public boolean isIdentity() {
			return true;
		}
    }

    /**
     * This inner class represents a compound converter (non-linear).
     */
    private static final class CompoundImpl extends AbstractConverter implements Compound {

        /**
         * For cross-version compatibility.
         */
        private static final long serialVersionUID = 2242882007946934958L;

        /**
         * Holds the first converter.
         */
        private final AbstractConverter left;

        /**
         * Holds the second converter.
         */
        private final AbstractConverter right;

        /**
         * Creates a compound converter resulting from the combined
         * transformation of the specified converters.
         *
         * @param  unitConverter the left converter.
         * @param  unitConverter2 the right converter.
         */
        private CompoundImpl(UnitConverter unitConverter, UnitConverter unitConverter2) {
            this.left = (AbstractConverter) unitConverter;
            this.right = (AbstractConverter) unitConverter2;
        }

        @Override
        public UnitConverter inverse() {
            return new CompoundImpl(right.inverse(), left.inverse());
        }

        @Override
        public double convert(double value) {
            return left.convert(right.convert(value));
        }

        @Override
        public BigDecimal convert(BigDecimal value, MathContext ctx) {
            return left.convert(right.convert(value, ctx), ctx);
        }


		public Number convert(Number value) {
			return left.convert(right.convert(value));
		}
        
        @Override
        public boolean equals(Object cvtr) {
            if (this == cvtr)
                return true;
            if (!(cvtr instanceof Compound))
                return false;
            Compound that = (Compound) cvtr;
            return (this.left.equals(that.getLeft()))
                    && (this.right.equals(that.getRight()));
        }

        @Override
        public int hashCode() {
            return left.hashCode() + right.hashCode();
        }

        @Override
        public boolean isLinear() {
            return left.isLinear() && right.isLinear();
        }

        public AbstractConverter getLeft() {
            return left;
        }

        public AbstractConverter getRight() {
            return right;
        }

		@Override
		public UnitConverter concatenate(UnitConverter converter) {
			return (converter == IDENTITY) ? this : new CompoundImpl(this, converter);
		}

		@Override
		public List<UnitConverter> getCompoundConverters() {
			return Arrays.asList((UnitConverter)new CompoundImpl(this, this));
		}
    }
}
