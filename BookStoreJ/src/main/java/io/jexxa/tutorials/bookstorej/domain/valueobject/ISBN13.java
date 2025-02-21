package io.jexxa.tutorials.bookstorej.domain.valueobject;

import io.jexxa.addend.applicationcore.ValueObject;
import io.jexxa.addend.applicationcore.ValueObjectFactory;
import org.apache.commons.lang3.Validate;

import java.util.Objects;

/**
 * IMPORTANT NOTE: This is a simplified ISBN13 number which only validates the checksum because this is sufficient for this tutorial
 */
@ValueObject
@SuppressWarnings("unused")
public class ISBN13
{
    private final String value;

    public ISBN13(String value)
    {
        Objects.requireNonNull(value);
        validateChecksum(value);

        this.value = value;
    }

    @ValueObjectFactory(ISBN13.class)
    public static ISBN13 createISBN(String value)
    {
        return new ISBN13(value);
    }

    public String getValue()
    {
        return value;
    }

    private void validateChecksum(String isbn13)
    {
        var digits = isbn13
                .replace("-","")
                .toCharArray();

        var digitSum = 0;

        for (var i = 0; i < digits.length - 1 ; ++i) //Exclude checksum value (which is at position digits.length -1)
        {

            var digitAsInt = Integer.parseInt(String.valueOf(digits[i]));
            if ( i % 2 == 0)
            {
                digitSum += digitAsInt;
            }
            else
            {
                digitSum += digitAsInt * 3;
            }
        }

        var calculatedCheckDigit = (10 - ( digitSum % 10 )) % 10;

        var expectedDigit =  Integer.parseInt(String.valueOf(digits[digits.length -1]));

        Validate.isTrue( calculatedCheckDigit == expectedDigit,
                "Invalid ISBN number: Expected checksum value is "
                        + calculatedCheckDigit
                        + " Given value is "
                        +  expectedDigit
        );
    }

}
