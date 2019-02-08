package com.hardcoreleveleditor.panels;

import javax.swing.*;
import java.awt.*;

public class GridCellPanel extends JPanel
{
    private final int cellWidth;
    private final int cellHeight;
    private Image cellImage;

    public GridCellPanel(final int cellWidth, final int cellHeight)
    {
        super();

        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.cellImage = null;

        setPreferredSize(new Dimension(cellWidth, cellHeight));
    }

    public void setImage(final Image image)
    {
        this.cellImage = image;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, cellWidth, cellHeight);
        g2.setColor(Color.black);
        g2.drawLine(0, cellHeight - 1, cellWidth - 1, cellHeight - 1);
        g2.drawLine(cellWidth - 1 , 0, cellWidth - 1, cellHeight - 1);

        if (cellImage != null)
        {
            g2.drawImage(cellImage, 0, 0, cellWidth, cellHeight, null);
        }

        g2.dispose();
    }
}
