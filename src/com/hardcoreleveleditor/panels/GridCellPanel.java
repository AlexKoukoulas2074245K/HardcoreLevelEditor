package com.hardcoreleveleditor.panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class GridCellPanel extends JPanel implements MouseListener
{
    private static GridCellPanel sSelectedGridCell;
    private static Image sSelectedCellImage;

    static
    {
        try
        {
            sSelectedCellImage = ImageIO.read(GridCellPanel.class.getResourceAsStream("/selected_cell.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private final int cellWidth;
    private final int cellHeight;

    private boolean isResourceCell;
    private Image cellImage;

    public GridCellPanel(final int cellWidth, final int cellHeight, final boolean isResourceCell)
    {
        super();

        addMouseListener(this);

        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.isResourceCell = isResourceCell;
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

        if (this == sSelectedGridCell)
        {
            g2.drawImage(sSelectedCellImage, 0, 0, cellWidth, cellHeight, null);
        }

        g2.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        sSelectedGridCell = this;
        getRootPane().revalidate();
        getRootPane().repaint();
    }

    @Override
    public void mousePressed(MouseEvent e)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }
}
