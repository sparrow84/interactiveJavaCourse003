echo After work
echo " "

git add *
git commit -m "$USER $(date +%Y%m%d-%H%M%S)"
git push origin master

echo " "
read -p "Press Enter..." PAUSE