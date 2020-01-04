<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="xml"
  encoding="windows-1251"
  omit-xml-declaration="no"
  indent="yes"
  media-type="text/xml"
  doctype-public="-//W3C//DTD XHTML 1.1//EN"
  doctype-system="http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd"
/>

<xsl:template match="/">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ru">
<head>
  <title>Мой первый XSLT</title>
</head>
<body>
  <div><xsl:text>Мой список:</xsl:text></div>
  <ol>
    <xsl:for-each select="/descendant-or-self::*/@*">
      <li>
        <xsl:if test="position() mod 2 = 0">
          <xsl:attribute name="style">background-color: #eee;</xsl:attribute>
        </xsl:if>
        <xsl:value-of select="concat(name(), ' = ', .)" />
      </li>
    </xsl:for-each>
  </ol>
  <div>
    <xsl:text>Разработчик парсера: </xsl:text>
    <a>
      <xsl:attribute name="href">
        <xsl:value-of select="system-property('xsl:vendor-url')" />
      </xsl:attribute>
      <xsl:attribute name="title">
        <xsl:value-of select="system-property('xsl:vendor-url')" />
      </xsl:attribute>
      <xsl:value-of select="system-property('xsl:vendor')" />
    </a>
  </div>
</body>
</html>
</xsl:template>

</xsl:stylesheet>