#!/bin/bash
input=$(cat)
path=$(echo "$input" | jq -r '.tool_input.file_path // empty')
[[ "$path" != *.java ]] && exit 0

content=$(echo "$input" | jq -r '.tool_input.content // .tool_input.new_string // empty')
[[ -z "$content" ]] && exit 0

v=""
grep -q "import lombok" <<< "$content" && v+="Lombok is forbidden. "
grep -qE "^\s*@Autowired\s*$" <<< "$content" && v+="Field injection — use constructor injection. "

[[ -n "$v" ]] && { echo "Convention violation in $path: $v" >&2; exit 2; }
exit 0