<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <!-- Attribute used for table border -->
    <xsl:attribute-set name="tableBorder">
        <xsl:attribute name="border">solid 0.1mm black</xsl:attribute>
    </xsl:attribute-set>
    <xsl:template match="items">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="simpleA4"
                                       page-height="29.7cm" page-width="22.0cm" margin="1cm">
                    <fo:region-body />
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="simpleA4">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="36pt" font-family="Times New Roman"
                              color="black" space-after="5mm" text-align="center" font-weight="bold">
                        Sales Report
                    </fo:block>
                    <fo:block font-size="16pt" font-family="Times New Roman"
                              color="black" space-after="5mm" text-align="center" font-weight="bold">
                    </fo:block>
                    <fo:block font-size="16pt" font-family="Times New Roman"
                              color="black" space-after="5mm" text-align="center" font-weight="bold">
                    </fo:block>
                    <fo:block font-size="16pt" font-family="Times New Roman"
                              color="black" space-after="5mm" text-align-last="justify" font-weight="bold">
                        Brand :
                        <xsl:value-of select="brand" />
                        <fo:leader leader-pattern="space"/>
                        From :
                        <xsl:value-of select="fromDate" />
                    </fo:block>
                    <fo:block font-size="16pt" font-family="Times New Roman"
                              color="black" space-after="5mm" text-align-last="justify" font-weight="bold">
                        Category :
                        <xsl:value-of select="category" />
                        <fo:leader leader-pattern="space"/>
                        To :
                        <xsl:value-of select="toDate" />
                    </fo:block>
                    <fo:block font-size="12pt" space-before="30pt">
                        <fo:table table-layout="fixed" width="100%"
                                  border-collapse="separate" line-height="25px" text-align="center">
                            <fo:table-column column-width="5cm" />
                            <fo:table-column column-width="5cm" />
                            <fo:table-column column-width="5cm" />
                            <fo:table-column column-width="5cm" />
                            <fo:table-header background-color="#f5f5f5">
                                <fo:table-cell
                                        xsl:use-attribute-sets="tableBorder">
                                    <fo:block font-weight="bold" font-family="Times New Roman">BRAND</fo:block>
                                </fo:table-cell>
                                <fo:table-cell
                                        xsl:use-attribute-sets="tableBorder">
                                    <fo:block font-weight="bold" font-family="Times New Roman">CATEGORY</fo:block>
                                </fo:table-cell>
                                <fo:table-cell
                                        xsl:use-attribute-sets="tableBorder">
                                    <fo:block font-weight="bold" font-family="Times New Roman">QUANTITY</fo:block>
                                </fo:table-cell>
                                <fo:table-cell
                                        xsl:use-attribute-sets="tableBorder">
                                    <fo:block font-weight="bold" font-family="Times New Roman">REVENUE</fo:block>
                                </fo:table-cell>
                            </fo:table-header>
                            <fo:table-body end-indent="0in">
                                <xsl:apply-templates select="item" />
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

    <xsl:template match="item">
        <fo:table-row >
            <fo:table-cell xsl:use-attribute-sets="tableBorder" font-family="Times New Roman">
                <fo:block>
                    <xsl:value-of select="brand" />
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="tableBorder" font-family="Times New Roman">
                <fo:block>
                    <xsl:value-of select="category" />
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="tableBorder" font-family="Times New Roman">
                <fo:block>
                    <xsl:value-of select="quantity" />
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="tableBorder" font-family="Times New Roman">
                <fo:block>
                    <xsl:value-of select="revenue" />
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>
</xsl:stylesheet>