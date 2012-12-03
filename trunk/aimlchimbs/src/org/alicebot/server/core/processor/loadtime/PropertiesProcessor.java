/*
    Alicebot Program D
    Copyright (C) 1995-2001, A.L.I.C.E. AI Foundation
    
    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public License
    as published by the Free Software Foundation; either version 2
    of the License, or (at your option) any later version.
    
    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, 
    USA.
*/

/*
    Code cleanup (4.1.3 [00] - October 2001, Noel Bush)
    - formatting cleanup
    - complete javadoc
    - made all imports explicit
*/

/*
    Further optimizations {4.1.3 [01] - November 2001, Noel Bush)
    - changed to extend StartupTagProcessor
    - moved to loadtime subpackage
*/

/*
    More fixes (4.1.3 [02] - November 2001, Noel Bush)
    - removed check of Graphmaster.loadtime (should be done by StartupFileParser)
    - changed to use changed BotProperty.setPredicateValue method
*/

package org.alicebot.server.core.processor.loadtime;

import java.io.File;

import org.alicebot.server.core.Graphmaster;
import org.alicebot.server.core.parser.StartupFileParser;
import org.alicebot.server.core.parser.XMLNode;
import org.alicebot.server.core.processor.ProcessorException;
import org.alicebot.server.core.util.Toolkit;
import org.alicebot.server.core.util.UserError;


/**
 */
public class PropertiesProcessor extends StartupElementProcessor
{
    public static final String label = "properties";


    public String process(int level, XMLNode tag, StartupFileParser parser) throws InvalidStartupElementException
    {
        // Does it have an href attribute?
        String href = getHref(tag);

        if (href.length() > 0)
        {
            try
            {
                return parser.
                		processResponse(
                			Toolkit.getFileContents(
                				Graphmaster.getWorkingDirectory() + File.separator + href));
            }
            catch (ProcessorException e)
            {
                throw new UserError(e);
            }
        }
        else
        {
            return parser.evaluate(level++, tag.XMLChild);
        }
    }
}

