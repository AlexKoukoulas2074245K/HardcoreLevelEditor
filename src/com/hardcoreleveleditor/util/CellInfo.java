package com.hardcoreleveleditor.util;

public class CellInfo
{
    public final int col;
    public final int row;
    public final boolean isResource;
    public CellInfo(final int col, final int row, final boolean isResource)
    {
        this.col = col;
        this.row = row;
        this.isResource = isResource;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null)
        {
            return false;
        }

        if (other instanceof CellInfo)
        {
            CellInfo ci = (CellInfo)other;
            return ci.col == this.col && ci.row == this.row && ci.isResource == this.isResource;
        }

        return false;
    }
}