#!/bin/bash
#
# Script to remove annoying files 
#
# removal.sh
# ==========
# Copyright (C) 2013-2014  Magdalen Berns <m.berns@sms.ed.ac.uk>
# A simple but useful bash script to get rid of annoying files with a single command. 
# just be careful not to delete things you want to keep!
#
# This script is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
# You should have received a copy of the GNU General Public License
# along with this script.  If not, see <http://www.gnu.org/licenses/>.

array=( '*.class' 'files/*.log' 'files/*.aux' 'files/*.synctex.gz' '*.*~' 'files/*.pdf')
for ((i=0; i<6; i++)); do
    rm ${array[${i}]}
done
