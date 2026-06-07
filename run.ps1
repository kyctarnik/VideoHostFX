$ErrorActionPreference = 'Stop'

Set-Location $PSScriptRoot

$targetClasses = Join-Path $PSScriptRoot 'target/classes'
if (Test-Path $targetClasses) {
    Remove-Item -Recurse -Force $targetClasses
}
New-Item -ItemType Directory -Force -Path $targetClasses | Out-Null

$modulePath = @(
    "$env:USERPROFILE\.m2\repository\org\openjfx\javafx-base\21.0.5\javafx-base-21.0.5-win.jar",
    "$env:USERPROFILE\.m2\repository\org\openjfx\javafx-graphics\21.0.5\javafx-graphics-21.0.5-win.jar",
    "$env:USERPROFILE\.m2\repository\org\openjfx\javafx-controls\21.0.5\javafx-controls-21.0.5-win.jar"
) -join ';'

$javaSources = Get-ChildItem -Recurse 'src/main/java' -Filter *.java | ForEach-Object { $_.FullName }
if (-not $javaSources) {
    throw 'Не найдены исходные Java-файлы в src/main/java.'
}

# Компилируем в байткод Java 17, чтобы избежать ошибки major.minor version 70.0.
javac --release 17 --module-path "$modulePath" -d "$targetClasses" $javaSources

# Копируем ресурсы (CSS и т.д.), иначе getResource вернет null.
Copy-Item -Path 'src/main/resources/*' -Destination $targetClasses -Recurse -Force

java --module-path "$targetClasses;$modulePath" --module ru.synergy.videohost/ru.synergy.videohost.App
